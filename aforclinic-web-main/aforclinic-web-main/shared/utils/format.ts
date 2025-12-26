export function currency(amount: number) {
  return Intl.NumberFormat('ko-KR').format(amount);
}
