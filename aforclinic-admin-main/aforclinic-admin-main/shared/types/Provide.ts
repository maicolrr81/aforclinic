export const navigationInjectionKey: InjectionKey<{
  /**
   * 네비게이션 열림 여부
   *
   * @default true
   */
  isNavigationOpen: Readonly<Ref<boolean>>;
  /**
   * 네비게이션 토클
   *
   * @returns
   */
  onToggleNavigation: () => void;
}> = Symbol('Navigation Injection Key');
