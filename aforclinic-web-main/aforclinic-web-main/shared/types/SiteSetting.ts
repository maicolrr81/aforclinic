export type SiteSettingKey = 'recommendedKeywords' | 'highlightedEvents';

export interface SiteSetting {
  settings: Record<SiteSettingKey, string>;
  highlightedEvents: Record<SiteSettingKey, string>;
}
