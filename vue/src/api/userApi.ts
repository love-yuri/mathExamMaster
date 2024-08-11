import { BaseApi, RequestType } from '@/common/base/baseApi';

/**
 * User API
 * Handles all user-related API requests
 */
class UserApi extends BaseApi {
  readonly baseUrl: string = '/user';

  login = (params: any) => {
    return this.add(RequestType.POST, 'login', params);
  };
}

export const userApi = new UserApi();
