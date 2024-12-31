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
