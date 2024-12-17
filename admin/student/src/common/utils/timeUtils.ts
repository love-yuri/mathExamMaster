/**
 * 将primevue的时间格式化成正常时间
 * @param time 待格式化时间
 */
export function formatPrimeVueTime(time: string) {
  return new Date(time).toLocaleString();
}
