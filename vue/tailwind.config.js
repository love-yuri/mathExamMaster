/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2023-11-05 20:42:03
 * @LastEditTime: 2024-04-11 22:36:42
 * @Description: tailwindcss配置文件
 */

export default {
  darkMode: 'class',
  content: ['./src/**/*.{vue, ts, tsx}'],
  theme: {
    extend: {
      zIndex: {
        '-1': '-1',
      },
    },
  },
};
