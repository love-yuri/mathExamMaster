/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-12-23 18:27:48
 * @LastEditTime: 2025-02-08 16:31:39
 * @Description: 考试状态
 */
import { acceptHMRUpdate, defineStore } from 'pinia';

export enum ExamStatus {
  NOT_STARTED,
  STARTED,
  FINISHED,
}
export const useExamStore = defineStore('exam-info', {
  actions: {
    finishExam() {
      this.status = ExamStatus.FINISHED;
    },
    startExam(releaseId: string) {
      this.status = ExamStatus.STARTED;
      this.releaseId = releaseId;
    },
  },
  persist: {
    // 持久化
    paths: ['releaseId', 'status'],
  },
  state: (): {
    releaseId?: string;
    status: ExamStatus;
  } => ({
    releaseId: undefined,
    status: ExamStatus.NOT_STARTED,
  }),
});

// 解决热更新问题
const hot = import.meta.hot;
if (hot) {
  hot.accept(acceptHMRUpdate(useExamStore, hot));
}
