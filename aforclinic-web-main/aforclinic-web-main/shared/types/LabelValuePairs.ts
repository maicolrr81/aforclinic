export interface LabelValuePair<T = string> {
  label: string;
  value: T;
}

export type LabelValuePairs<T = string> = LabelValuePair<T>[];
