<template>
  <v-footer padless color="">
    <v-col class="text-center" cols="12">
      {{ new Date().getFullYear() }} — <strong>@cloudcentrik systems</strong>
      <ServerInfo />
    </v-col>
  </v-footer>
</template>
<script>
import ServerInfo from "@/components/ServerInfo.vue";
export default {
  name: "Footer",
  components: {
    ServerInfo,
  },
  data: () => ({
    icons: [
      "fab fa-facebook",
      "fab fa-twitter",
      "fab fa-google-plus",
      "fab fa-linkedin",
      "fab fa-instagram",
    ],
  }),
};
</script>
