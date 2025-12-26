import type { ValidationRule } from 'quasar';

export const idRules: ValidationRule[] = [
  (val: string) => !!val || '아이디을 입력하세요',
  (val: string) => (val.length <= 100) || '아이디가 100자를 초과하였습니다.',
];

export const emailRules: ValidationRule[] = [
  (val: string) => !!val || '아이디(이메일)을 입력하세요',
  (val: string) => /^[\w.%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i.test(val) || '아이디가 이메일 형식이 아닙니다.',
];

export const roleRules: ValidationRule[] = [
  (val: string) => !!val || '역할을 입력하세요.',
];

export const certificationNumberRules: ValidationRule[] = [
  (val: string) => (val.length === 6) || '인증번호 형식이 아닙니다.',
];

export const passwordRules: ValidationRule[] = [
  (val: string) => !!val || '비밀번호를 입력하세요',
  (val: string) => ((val.length >= 8 && val.length <= 16)
    && (/[a-z]/i.test(val))
    && (/\d/.test(val))
    && (/[!@#$%^&*]/.test(val))
  ) || `영문, 숫자, 특수문자(!@#$%^&*)를 포함한 8~16자`,
];

export const nameRules: ValidationRule[] = [
  (val: string) => !(val && val.length > 100) || '이름이 100자를 초과하였습니다.',
];

export const dateRules: ValidationRule[] = [
  (val: string) => {
    if (!val) { return true; } // 빈 값 허용
    const dayjs = useDayjs();
    return dayjs(val, 'YYYY-MM-DD', true).isValid() || '올바른 날짜 형식(YYYY-MM-DD)을 입력하세요';
  },
];

export const requireDateRules: ValidationRule[] = [
  (val: string) => !!val || '일자를 입력하세요.',
  // (val: string) => (!val || /^(\d{4})-(\d{2})-(\d{2})$/.test(val)) || '올바른 날짜 형식(YYYY-MM-DD)을 입력하세요',
  // (val: string) => {
  //   if (val) {
  //     const [year, month, day] = val.split('-');

  //     const date = new Date(year, String(Number(month) - 1), day);

  //     return (!val || (String(date.getFullYear()) === year && date.getMonth() + 1 === Number(month) && String(date.getDate()) === day)) || '유효한 날짜를 입력하세요';
  //   }
  //   return true;
  // },
];

export const phoneNumberRules: ValidationRule[] = [
  (val: string) => (!val || /^\d{10,11}$/.test(val)) || '전화번호가 유효하지 않습니다. (10~11자리 숫자)',
];
