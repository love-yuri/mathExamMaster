<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-09-10 23:36:21
 * @LastEditTime: 2024-09-21 22:57:11
 * @Description: 
-->
<template>
  <div class="p-2">
    <Toolbar
      :default-config="toolbarConfig"
      :editor="editorRef"
      style="border-bottom: 1px solid #ccc"
    />
    <Editor
      v-model="valueHtml"
      :default-config="editorConfig"
      style="height: 500px; overflow-y: hidden"
      @custom-paste="customPaste"
      @on-created="handleCreated"
    />
    {{ valueHtml }}
  </div>
</template>
<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css'; // 引入 css
import { type IDomEditor, type IEditorConfig } from '@wangeditor/editor';
import { onBeforeUnmount, ref, shallowRef } from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
import { systemApi } from '#/api/systemApi';
import { systemFileApi } from '#/api/systemFileApi';

type InsertFnType = (url: string, alt: string, href: string) => void;
const editorRef = shallowRef();
const editorConfig: Partial<IEditorConfig> = {
  MENU_CONF: {
    uploadImage: {
      async customUpload(file: File, insertFn: InsertFnType) {
        const res = await systemApi.upload({
          file,
        });
        // const res = await systemFileApi.getFile('1837387808326205441');
        const url = systemFileApi.getFile(res.id);
        insertFn(url, '', '');
      },
      fieldName: 'custom-field-name',
      server: '/api/upload-image',
    },
  },
};

// 内容 HTML
const valueHtml = ref('<p>hello</p>');

const toolbarConfig = {};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor === null) return;
  editor.destroy();
});

const handleCreated = (editor: any) => {
  // 记录 editor 实例，重要！
  editorRef.value = editor;
  // editor.disable();
};

function customPaste(editor: IDomEditor, event: ClipboardEvent): boolean {
  // 获取粘贴的html部分（？？没错粘贴word时候，一部分内容就是html），该部分包含了图片img标签
  let html: string | undefined = event.clipboardData?.getData('text/html');

  // 获取rtf数据（从word、wps复制粘贴时有），复制粘贴过程中图片的数据就保存在rtf中
  const rtf = event.clipboardData?.getData('text/rtf');
  const pictPattern = /\\pict.*?([\da-fA-F]+)}/gs;

  // 查找所有匹配的图片数据
  const matches = rtf?.matchAll(pictPattern);
  const images: any[] = [];

  for (const match of matches) {
      const hexData = match[1].replace(/\s+/g, '');  // 移除多余的空格或换行符
      // const binaryData = hexToBytes(hexData);
      images.push(hexData);
  }
  console.log('yuri: ', images);

  if (html && rtf) {
    // 该条件分支即表示要自定义word粘贴

    // 列表缩进会超出边框，直接过滤掉
    html = html.replaceAll(/text-indent:-(.*?)pt/gi, '');

    // 从html内容中查找粘贴内容中是否有图片元素，并返回img标签的属性src值的集合
    const imgSrcs = findAllImgSrcsFromHtml(html);
    console.log('yuri:', imgSrcs);

    // 如果有
    if (imgSrcs.length > 0) {
      // 从rtf内容中查找图片数据
      const rtfImageData = extractImageDataFromRtf(rtf);
      console.log('yuri:', rtfImageData);

      // 如果找到
      if (rtfImageData.length > 0) {
        // 执行替换：将html内容中的img标签的src替换成ref中的图片数据，如果上面上传了则为图片路径
        html = replaceImagesFileSourceWithInlineRepresentation(
          html,
          imgSrcs,
          rtfImageData,
        );
        editor.dangerouslyInsertHtml(html!);
      }

      // 阻止默认的粘贴行为
      event.preventDefault();
      return false;
    }
  }
  return true;
}

/**
 * 从html代码中匹配返回图片标签img的属性src的值的集合
 * @param htmlData
 * @return Array
 */
function findAllImgSrcsFromHtml(htmlData: string): Array<string> {
  const imgReg = /<img.*?(?:>|\/>)/gi; // 匹配图片中的img标签
  const srcReg = /src=["']?([^"']*)["']?/i; // 匹配图片中的src

  const arr = htmlData.match(imgReg); // 筛选出所有的img
  if (!arr || (Array.isArray(arr) && arr.length === 0)) {
    return [];
  }

  const srcArr: string[] = [];
  for (const element of arr) {
    const src = element.match(srcReg) ?? [];
    if (src && src.length > 1) {
      srcArr.push(src[1]!);
    }
  }

  return srcArr;
}

/**
 * 从rtf内容中匹配返回图片数据的集合
 * @param rtfData
 * @return Array
 */
function extractImageDataFromRtf(rtfData: string): any[] {
  if (!rtfData) {
    return [];
  }

  const regexPictureHeader =
    /\{\\pict[\s\S]+?(\{\\\*\\blipuid\s?[\dA-Fa-f]+)[\s}]*/;
  const regexPicture = new RegExp(
    `(?:(${regexPictureHeader.source}))([\\da-fA-F\\s]+)\\}`,
    'g',
  );
  const images = rtfData.match(regexPicture);
  const result = [];

  if (images) {
    for (const image of images) {
      let imageType = false;

      if (image.includes(String.raw`\pngblip`)) {
        imageType = 'image/png';
      } else if (image.includes(String.raw`\jpegblip`)) {
        imageType = 'image/jpeg';
      }

      if (imageType) {
        result.push({
          hex: image
            .replace(regexPictureHeader, '')
            .replaceAll(/[^\da-f]/gi, ''),
          type: imageType,
        });
      }
    }
  }

  return result;
}
/**
 * 将html内容中img标签的属性值替换
 * @param htmlData html内容
 * @param imageSrcs html中img的属性src的值的集合
 * @param imagesHexSources rtf中图片数据的集合，与html内容中的img标签对应
 * @param isBase64Data 是否是Base64的图片数据
 * @return String
 */
function replaceImagesFileSourceWithInlineRepresentation(
  htmlData: string,
  imageSrcs: string[],
  imagesHexSources: any,
  isBase64Data = true,
) {
  if (imageSrcs.length === imagesHexSources.length) {
    for (const [i, imageSrc] of imageSrcs.entries()) {
      const newSrc = isBase64Data
        ? `data:${imagesHexSources[i].type};base64,${_convertHexToBase64(imagesHexSources[i].hex)}`
        : imagesHexSources[i];

      htmlData = htmlData.replace(imageSrc, newSrc);
    }
  }

  return htmlData;
}

/**
 * 十六进制转base64
 */
function _convertHexToBase64(hexString: any) {
  return btoa(
    hexString
      .match(/\w{2}/g)
      .map((char: any) => {
        return String.fromCodePoint(Number.parseInt(char, 16));
      })
      .join(''),
  );
}
</script>
