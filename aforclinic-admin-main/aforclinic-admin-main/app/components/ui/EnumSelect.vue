<script setup lang="ts">
import type { QSelectProps } from 'quasar';
import { USER_ADMIN_ROLE_OPTIONS, USER_ROLE_OPTIONS, USER_STATUS_OPTIONS } from '~~/shared/constants/user.enum';

interface EnumSelectProps extends QSelectProps {
  name: `role` | `adminrole` | `status`;
  dense?: boolean;
  clearable?: boolean;
  readonly?: boolean;
}

const {
  name,
  dense = false,
  clearable = false,
  readonly = false,
} = defineProps<EnumSelectProps>();

const modelValue = defineModel<QSelectProps[`modelValue`]>({ default: undefined });

const label = computed(() => {
  switch (name) {
    case `role`: return `역할`;
    case `adminrole`: return `역할`;
    case `status`: return `상태`;
    default: return ``;
  }
});

const options = computed(() => {
  switch (name) {
    case `role`: return USER_ROLE_OPTIONS;
    case `adminrole`: return USER_ADMIN_ROLE_OPTIONS;
    case `status`: return USER_STATUS_OPTIONS;
    default: return [];
  }
});
</script>

<template>
  <QSelect
    v-model.trim="modelValue"
    :label="label"
    :options="options"
    emit-value
    map-options
    option-label="text"
    option-value="value"
    :dense="dense"
    :clearable="clearable"
    :readonly="readonly"
  />
</template>
