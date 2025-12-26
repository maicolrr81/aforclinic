export interface DialogOptions {
  title?: string;
  message: string;
  showCancel?: boolean;
  confirmText?: string;
  cancelText?: string;
}

interface DialogState {
  open: boolean;
  options: DialogOptions;
  resolve: ((value: boolean) => void) | null;
}

export default defineNuxtPlugin(() => {
  const state = reactive<DialogState>({
    open: false,
    options: {
      message: '',
      showCancel: false,
    },
    resolve: null,
  });

  // 다이얼로그 표시 함수
  const show = (options: DialogOptions): Promise<boolean> => {
    return new Promise((resolve) => {
      state.options = {
        title: '',
        confirmText: '확인',
        cancelText: '취소',
        ...options,
      };
      state.open = true;
      state.resolve = resolve;
    });
  };

  // 다이얼로그 닫기 함수
  const close = (result: boolean): void => {
    state.open = false;
    if (state.resolve) {
      state.resolve(result);
      state.resolve = null;
    }
  };

  // alert 다이얼로그 표시
  const alert = (message: string, options: Omit<DialogOptions, 'message' | 'showCancel'> = {}): Promise<boolean> => {
    return show({
      message,
      showCancel: false,
      ...options,
    });
  };

  // confirm 다이얼로그 표시
  const confirm = (message: string, options: Omit<DialogOptions, 'message'> = {}): Promise<boolean> => {
    return show({
      message,
      showCancel: true,
      ...options,
    });
  };

  return {
    provide: {
      dialog: {
        state,
        close,
        alert,
        confirm,
      },
    },
  };
});
