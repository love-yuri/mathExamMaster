/*
 * @Author: love-yuri yuri2078170658@gmail.com
 * @Date: 2024-08-21 00:16:15
 * @LastEditTime: 2024-08-21 00:52:46
 * @Description: 测试api
 */
import { BaseApi, RequestType } from '@/common/base/baseApi';

/**
 * User API
 * Handles all user-related API requests
 */
class TestApi extends BaseApi {
  readonly baseUrl: string = '/test';

  hello = () => {
    return this.add(RequestType.GET, 'hello');
  };
}

export const testApi = new TestApi();
