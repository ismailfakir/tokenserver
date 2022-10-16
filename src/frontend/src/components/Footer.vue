<template>
  <v-footer padless color="">
    <v-col
      class="text-center"
      cols="12"
    >
      {{ new Date().getFullYear() }} — <strong>@cloudcentrik systems</strong>
    </v-col>
  </v-footer>
</template>
<script>
  export default {
      name: 'Footer',
    data: () => ({
      icons: [
        'fab fa-facebook',
        'fab fa-twitter',
        'fab fa-google-plus',
        'fab fa-linkedin',
        'fab fa-instagram',
      ],
    }),
  }
</script>