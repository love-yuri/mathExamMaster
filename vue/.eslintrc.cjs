/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-04-11 21:46:04
 * @LastEditTime: 2024-08-11 15:50:26
 * @Description: eslint config
 */
module.exports = {
  // 指定这是根配置文件，ESLint 在寻找配置时会停止在此
  root: true,

  // 定义环境变量，分别启用浏览器、Node.js 和 ES6 环境
  env: {
    browser: true,
    node: true,
    es6: true,
  },

  // 指定解析器，用于解析 Vue 文件
  parser: 'vue-eslint-parser',

  // 指定要使用的 ESLint 插件，这里使用 Vue 插件
  plugins: ['vue'],

  // 解析器选项，指定 TypeScript 解析器和 ECMAScript 版本等
  parserOptions: {
    parser: '@typescript-eslint/parser',
    ecmaVersion: 2020,
    sourceType: 'module', // 指定使用 ECMAScript 模块
    jsxPragma: 'React', // 指定 JSX 使用的库
    ecmaFeatures: {
      jsx: true, // 启用 JSX 语法
    },
  },

  // 扩展配置，使用 Vue 3 推荐的配置、Prettier 以及 TypeScript 的推荐配置
  extends: ['plugin:vue/vue3-recommended', 'prettier', 'plugin:@typescript-eslint/recommended', 'plugin:prettier/recommended'],

  // 自定义规则设置
  rules: {
    // 强制代码最大长度为 140 个字符，但忽略注释
    'max-len': ['error', { code: 140, tabWidth: 2, ignoreComments: true }],

    // 确保在 script setup 中声明的变量被使用
    'vue/script-setup-uses-vars': 'error',

    // 对 @ts-ignore 注释的禁止
    '@typescript-eslint/ban-ts-ignore': 'off',

    // 对显式函数返回类型的要求
    '@typescript-eslint/explicit-function-return-type': [
      'error',
      {
        allowExpressions: true, // 允许表达式形式的函数（如箭头函数）省略返回类型
        allowTypedFunctionExpressions: true, // 已经推断出类型的函数允许省略返回类型
        allowHigherOrderFunctions: true, // 高阶函数允许省略返回类型
        allowConciseArrowFunctionExpressionsStartingWithVoid: true, // 简短的箭头函数如果没有返回值可以省略返回类型
      },
    ],

    // 对 any 类型的使用限制
    '@typescript-eslint/no-explicit-any': 'warn',

    // 对 require 语法的禁止
    '@typescript-eslint/no-var-requires': 'error',

    // 关闭对空函数的禁止
    '@typescript-eslint/no-empty-function': 'off',

    // 对自定义事件命名方式的要求
    'vue/custom-event-name-casing': 'warn',

    // 未定义前使用变量的禁止
    'no-use-before-define': 'error',
    '@typescript-eslint/no-use-before-define': 'error',

    // 关闭对 @ts-comment 注释的禁止
    '@typescript-eslint/ban-ts-comment': 'off',

    // 关闭对某些类型的禁止
    '@typescript-eslint/ban-types': 'off',

    // 关闭对非空断言的禁止
    '@typescript-eslint/no-non-null-assertion': 'off',

    // 关闭对显式模块边界类型的要求
    '@typescript-eslint/explicit-module-boundary-types': 'off',

    // 未使用变量的警告，但忽略下划线开头的变量或参数
    '@typescript-eslint/no-unused-vars': [
      'error',
      {
        argsIgnorePattern: '^_',
        varsIgnorePattern: '^_',
      },
    ],
    'no-unused-vars': [
      'error',
      {
        argsIgnorePattern: '^_',
        varsIgnorePattern: '^_',
      },
    ],

    // 关闭函数括号前的空格要求
    'space-before-function-paren': 'off',

    // Vue 相关的规则设置
    'vue/attributes-order': 'warn', // 属性顺序要求
    'vue/one-component-per-file': 'error', // 每个文件一个组件的限制
    'vue/html-closing-bracket-newline': 'off', // 关闭 HTML 闭合标签换行的要求
    'vue/max-attributes-per-line': 'off', // 关闭每行最大属性数的限制
    'vue/multiline-html-element-content-newline': 'warn', // 关闭多行 HTML 元素内容换行要求
    'vue/singleline-html-element-content-newline': 'off', // 关闭单行 HTML 元素内容换行要求
    'vue/attribute-hyphenation': 'off', // 关闭属性连字符要求
    'vue/require-default-prop': 'off', // 关闭默认 prop 的要求
    'vue/require-explicit-emits': 'off', // 关闭显式 emit 声明的要求

    // 对 HTML 自闭合规则进行配置
    'vue/html-self-closing': [
      'error',
      {
        html: {
          void: 'always', // 自闭合标签
          normal: 'never', // 普通 HTML 标签不自闭合
          component: 'always', // Vue 组件自闭合
        },
        svg: 'always', // SVG 标签自闭合
        math: 'always', // MathML 标签自闭合
      },
    ],
  },
};
