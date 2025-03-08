<!--
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2025-03-08 21:15:25
 * @LastEditTime: 2025-03-08 21:33:36
 * @Description: 
-->
<template>
  <EchartsUI ref="chartRef" />
</template>

<script lang="ts" setup>
import type { ScoreDetailResult } from '@yuri/types';

import type { EchartsUIType } from '@vben/plugins/echarts';

import { onMounted, ref } from 'vue';

import { EchartsUI, useEcharts } from '@vben/plugins/echarts';

const props = defineProps<{
  data: ScoreDetailResult;
}>();

const chartRef = ref<EchartsUIType>();
const { renderEcharts } = useEcharts(chartRef);

onMounted(() => {
  renderEcharts({
    legend: {
      bottom: '2%',
      left: 'center',
    },
    series: [
      {
        animationDelay() {
          return Math.random() * 100;
        },
        animationEasing: 'exponentialInOut',
        animationType: 'scale',
        avoidLabelOverlap: false,
        color: [
          '#5ab1ef',
          '#b6a2de',
          '#67e0e3',
          '#2ec7c9',
          '#ffb980',
          '#d87a80',
          '#8d98b3',
          '#e5cf0d',
          '#97b552',
          '#95706d',
        ],
        data: [
          { name: '60以下', value: props.data.below60 },
          { name: '60以上', value: props.data.over60 },
          { name: '70以上', value: props.data.over70 },
          { name: '80以上', value: props.data.over80 },
          { name: '90以上', value: props.data.over90 },
        ],
        emphasis: {
          label: {
            fontSize: '12',
            fontWeight: 'bold',
            show: true,
          },
        },
        itemStyle: {
          // borderColor: '#fff',
          borderRadius: 10,
          borderWidth: 2,
        },
        label: {
          position: 'center',
          show: false,
        },
        labelLine: {
          show: false,
        },
        name: '得分分布',
        radius: ['40%', '65%'],
        type: 'pie',
      },
    ],
    tooltip: {
      trigger: 'item',
    },
  });
});
</script>
