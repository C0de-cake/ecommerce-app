export type AlertType = 'SUCCESS' | 'ERROR';

export interface ToastInfo {
  body: string;
  type: AlertType;
}
