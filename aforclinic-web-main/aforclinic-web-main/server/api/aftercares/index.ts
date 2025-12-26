import type { Aftercare } from '#shared/types/Aftercare';

export default defineEventHandler(() => {
  return useStorage('assets:server').getItem<Aftercare[]>(`aftercare.json`);
});
