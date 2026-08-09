export interface LoginResponse {
  success: boolean;
  message: string;
  data: {
    username: string;
    token: string;
  };
}
