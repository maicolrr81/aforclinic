export interface Navigation {
  text: string;
  link: string;
  icon?: string;
  target?: string;
  items: Omit<Navigation, 'items'>[];
};

export interface HeaderConfig {
}

export interface DrawerConfig {
  navs: Omit<Navigation, 'link'>[];
}

export interface FooterConfig {
}

export interface ThemeConfig {
  /**
   * Header Config
   */
  header: HeaderConfig;
  /**
   * Drawer Config
   */
  drawer: DrawerConfig;
  /**
   * Footer Config
   */
  footer: FooterConfig;
}

export interface AppConfig {
  /**
   * Theme Config
   */
  theme: ThemeConfig;
}
