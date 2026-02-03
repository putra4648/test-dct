<template>
    <button :type="$props.type" class="bg-blue-500 w-fit rounded xl py-3 px-2 text-white hover:bg-blue-600"
        @click="props.onClick">{{
            props.name }} <slot></slot>
    </button>
</template>

<script setup lang="ts">
import type { PropType } from 'vue';

const props = defineProps({
    name: String,
    onClick: {
        type: Function as PropType<(payload: PointerEvent) => void>,
        required: true
    },
    type: {
        type: String as PropType<'button' | 'submit' | 'reset'>,
        default: 'button'
    }
})

</script>