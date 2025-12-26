import type { QDialogOptions } from 'quasar';
import { FetchError } from 'ofetch';

export default defineNuxtPlugin(() => {
  const { $q } = useNuxtApp();

  function alert(message: string, options?: Omit<QDialogOptions, `message`>): Promise<boolean> {
    return new Promise(resolve => $q.dialog({
      title: `알림`,
      ok: {
        color: `blue`,
        label: `확인`,
        flat: true,
      },
      persistent: true,
      ...options,
      message,
    })
      .onOk(() => resolve(true))
      .onCancel(() => resolve(true)));
  }

  function confirm(message: string, options?: Omit<QDialogOptions, `message`>): Promise<boolean> {
    return new Promise(resolve => $q.dialog({
      title: `알림`,
      ok: {
        color: `blue`,
        label: `확인`,
        flat: true,
      },
      cancel: {
        color: `grey`,
        label: `취소`,
        flat: true,
      },
      persistent: true,
      ...options,
      message,
    })
      .onOk(() => resolve(true))
      .onCancel(() => resolve(false))
      .onDismiss(() => resolve(false)));
  }

  function error(error: unknown, defaultMessage: string = `알 수 없는 오류가 발생했습니다.`): Promise<boolean> {
    let message = defaultMessage;
    if (error instanceof FetchError) {
      message = (error.data as ApiErrorResponse).message ?? message;
    }
    return alert(message);
  }

  return {
    provide: {
      dialog: {
        alert,
        confirm,
        error,
      },
    },
  };
});
