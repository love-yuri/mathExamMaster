/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-23 18:14:15
 * @LastEditTime: 2024-09-24 18:33:44
 * @Description: RTF 转 JPG
 */

import { systemFileApi } from '#/api/systemFileApi';

/**
 * 图片文件
 */
export interface ImageFile {
  height?: string;
  name: string;
  url: string;
  width?: string;
}

type LoadImgResult = {
  imgList: ImageFile[];
  newHtml: string;
};

/**
 * 十六进制转文件
 * 发送至服务器进行解析
 */
export function hexToFile(hex: string, fileName: string): File {
  if (hex.length % 2 !== 0) {
    throw new Error('十六进制数据错误!!!');
  }

  const bytes = new Uint8Array(hex.length / 2);
  for (let i = 0; i < hex.length; i += 2) {
    bytes[i / 2] = Number.parseInt(hex.slice(i, i + 2), 16);
  }

  const blob = new Blob([bytes], { type: 'application/octet-stream' });

  return new File([blob], fileName, { type: 'application/octet-stream' });
}

/**
 * 从html代码中匹配返回图片标签img的属性src的值的集合
 * @param htmlData
 * @return LoadImgResult 新的html数据，图片列表
 */
function findAllImgSrcsFromHtml(htmlData: string): LoadImgResult {
  const imgReg = /<img.*?(?:>|\/>)/gi; // 匹配图片中的img标签
  const srcReg = /src=["']?([^"']*)["']?/i; // 匹配图片中的src

  const arr = htmlData.match(imgReg); // 筛选出所有的img
  if (!arr || (Array.isArray(arr) && arr.length === 0)) {
    return {
      imgList: [],
      newHtml: htmlData,
    };
  }

  const srcArr: ImageFile[] = [];
  for (const element of arr) {
    const src = element.match(srcReg) ?? [];
    const img: ImageFile = {
      name: src[1]?.split('/').pop() || 'temp',
      url: src[1] ?? '',
    };

    if (element.includes('width') && element.includes('height')) {
      const widthMatch = element.match(/width=["']?([^"']*)["']?/i);
      const heightMatch = element.match(/height=["']?([^"']*)["']?/i);
      img.width = widthMatch?.[1];
      img.height = heightMatch?.[1];
      let newElement = element.replace(widthMatch?.[0] ?? '', '');
      newElement = newElement.replace(heightMatch?.[0] ?? '', '');
      newElement = `${newElement.slice(0, Math.max(0, newElement.length - 1))} style="width: ${img.width}px; height: ${img.height}px;" >`;
      htmlData = htmlData.replace(element, newElement);
    }
    if (src && src.length > 1) {
      srcArr.push(img);
    }
  }

  return {
    imgList: srcArr,
    newHtml: htmlData,
  };
}

/**
 * 将html内容中img标签的属性值替换
 * @param htmlData html内容
 * @param imageFiles html中img的属性src的值的集合
 * @param rtf rtf数据
 * @return 新的html数据
 */
async function replaceImagesFile(
  htmlData: string,
  imageFiles: ImageFile[],
  rtf: string,
): Promise<string> {
  // eslint-disable-next-line prettier/prettier, unicorn/better-regex, regexp/strict
  const regexPictureHeader = /{\\pict[\s\S]+?({\\\*\\blipuid\s?[\da-fA-F]+)[\s}]*/;
  // eslint-disable-next-line prefer-template, prettier/prettier, regexp/no-super-linear-backtracking, regexp/strict, regexp/no-useless-non-capturing-group, regexp/no-misleading-capturing-group, unicorn/prefer-string-raw
  const regexPicture = new RegExp('(?:(' + regexPictureHeader.source + '))([\\da-fA-F\\s]+)\\}','g',);
  const images = rtf.match(regexPicture);
  const imageSize = imageFiles.length ?? 0;
  console.log('yuri: ', imageSize);
  console.log('yuri: ', images?.length);
  if (!images || images.length % imageSize !== 0) {
    throw new Error('图片数量不匹配');
  }
  for (let i = 0; i < imageSize; i++) {
    const img: ImageFile = imageFiles[i]!;
    const step = images.length / imageSize;
    let imageHex: string = images[i * step]!;
    // 清除空行
    imageHex = imageHex
      .replace(regexPictureHeader, '')
      .replaceAll(/[^\da-f]/gi, '');

    const res = await systemFileApi.wmfToJpg({
      file: hexToFile(imageHex, img.name),
    });
    htmlData = htmlData.replace(img.url, systemFileApi.getFile(res.id));
  }

  return htmlData;
}

/**
 * 从html数据和rtf数据中加载图片
 */
export function loadHtmlImg(html: string, rtf: string): Promise<string> {
  html = html.replaceAll(/text-indent:-(.*?)pt/gi, '');
  // 去除包括img标签的span标签
  html = html.replaceAll(/<span>\s*(<img[^>]*>)\s*<\/span>/gi, '$1');

  // 从html内容中查找粘贴内容中是否有图片元素，并返回img标签的属性src值的集合
  const { imgList, newHtml } = findAllImgSrcsFromHtml(html);
  console.log('yuri: ', imgList);
  html = newHtml;
  if (imgList.length > 0) {
    return replaceImagesFile(html, imgList, rtf);
  }
  // 如果没有图片元素，直接返回html
  return Promise.resolve(html);
}
