<template>
  <v-snackbar
    v-model="snackbar.visible"
    :color="snackbar.color"
    :multi-line="snackbar.mode === 'multi-line'"
    :timeout="snackbar.timeout"
    :top="snackbar.position === 'top'"
    right
  >
    <v-layout align-center pr-4>
      <v-icon class="pr-3" dark large>{{ snackbar.icon }}</v-icon>
      <v-layout column>
        <div>
          <strong>{{ snackbar.title }}</strong>
        </div>
        <div>{{ snackbar.text }}</div>
      </v-layout>
    </v-layout>
    <v-btn v-if="snackbar.timeout === 0" icon @click="snackbar.visible = false">
      <v-icon>clear</v-icon>
    </v-btn>
  </v-snackbar>
</template>
<script>
export default {
  name: 'Toast',
  props: {
    /* text: {
          type: String,
          required: true
      },
      timeout: {
          type: Number,
          required: false,
          default: 2000
      } */
  },
  data: () => ({
    snackbar: {
      color: null,
      icon: null,
      mode: null,
      position: 'top',
      text: null,
      timeout: 1500,
      title: null,
      visible: false
    }
  }),
  methods: {
    show(type, message, showTime) {
      if (!type) return;
      switch (type) {
        case 'error':
          this.snackbar = {
            color: 'error',
            icon: 'error',
            mode: 'multi-line',
            position: 'top',
            timeout: showTime,
            title: 'Error',
            text: message,
            visible: true
          };
          break;
        case 'info':
          this.snackbar = {
            color: 'info',
            icon: 'info',
            mode: 'multi-line',
            position: 'top',
            timeout: showTime,
            title: 'Information',
            text: message,
            visible: true
          };
          break;
        case 'success':
          this.snackbar = {
            color: 'success',
            icon: 'check_circle',
            mode: 'multi-line',
            position: 'top',
            timeout: showTime,
            title: 'Success',
            text: message,
            visible: true
          };
          break;
        case 'warning':
          this.snackbar = {
            color: 'warning',
            icon: 'warning',
            mode: 'multi-line',
            position: 'top',
            timeout: showTime,
            title: 'Warning',
            text: message,
            visible: true
          };
          break;
      }
    }
  }
};
</script>