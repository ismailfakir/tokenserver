<template>
  <v-chip class="ma-2" color="success" outlined>
    <v-icon left> mdi-server-plus </v-icon>
    {{ msg }}
  </v-chip>
</template>

<script>
import axios from "axios";
export default {
  name: "HelloWorld",
  data() {
    return {
      msg: "",
    };
  },
  mounted() {
    axios
      .get("/api/serverinfo")
      .then((response) => {
        this.msg = response.data;
      })
      .catch((error) => {
        this.msg = error.toJSON();
      });
  },
};
</script>
