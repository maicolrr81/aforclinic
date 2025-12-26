export interface Aftercare {
  name: string;
  items: string[] | {
    text: string;
    items: string[];
  }[];
}
