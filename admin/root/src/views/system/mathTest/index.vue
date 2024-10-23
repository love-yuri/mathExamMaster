<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-10-20 17:17:11
 * @LastEditTime: 2024-10-23 19:31:54
 * @Description: 
-->
<template>
  <div>
    <!-- Mathfield 编辑器 -->
    <div ref="mathfield" class="p-2"></div>

    <!-- 显示 Mathfield 中输入的公式 -->
    {{ latexExpression }}

    <!-- <math-field>f(x) = \sin(x+\pi)</math-field> -->
    <button @click="visible = true">打开</button>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { MathfieldElement } from 'mathlive';

const visible = ref(false);
const mathfield = ref<HTMLDivElement>(); // 用于引用 Mathfield 的 DOM 节点
const latexExpression = ref(''); // 用于存储 LaTeX 表达式

onMounted(() => {
  // 创建 Mathfield 实例
  const mf = new MathfieldElement();
  mf.value = String.raw`\frac{a}{b}`; // 设置初始表达式
  mf.className = 'w-full text-[32px]'; // 设置 Mathfield 的样式类名
  mf.style.fontSize = '34px'; // 设置字体大小
  mf.addEventListener('input', () => {
    latexExpression.value = mf.getValue('latex'); // 获取当前 LaTeX 表达式
  });

  // 将 Mathfield 添加到 DOM 中
  mathfield.value?.append(mf);
});
</script>
