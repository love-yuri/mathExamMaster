/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-02-27 11:55:53
 * @LastEditTime: 2025-03-03 16:39:55
 * @Description:
 */
/**
 * 防抖函数
 * @param fn - 需要延迟执行的函数
 * @param delay - 延迟时间（毫秒）
 * @returns 包装后的防抖函数
 */
export function debounce<T extends (...args: any[]) => any>(
  fn: T,
  delay: number,
): (...args: Parameters<T>) => void {
  let timer: ReturnType<typeof setTimeout>;

  return (...args: Parameters<T>) => {
    clearTimeout(timer); // 清除之前的定时器
    timer = setTimeout(() => {
      fn(...args); // 延迟后执行函数
    }, delay);
  };
}

/**
 * 比较 两个数组是否相同，不关心顺序
 */
export function areArraysEqual<T>(arr1: T[], arr2: T[]): boolean {
  if (arr1.length !== arr2.length) return false;

  const set1 = new Set(arr1);
  const set2 = new Set(arr2);

  return (
    arr1.every((item) => set2.has(item)) && arr2.every((item) => set1.has(item))
  );
}
