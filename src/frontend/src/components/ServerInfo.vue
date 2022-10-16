<template>
  <v-container>

    <v-row class="text-center">
      <v-col class="mb-4">
        <h1 class="display-2 font-weight-bold mb-3">
        <v-chip>{{ msg }}</v-chip>
        </h1>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import axios from 'axios'
  export default {
    name: 'HelloWorld',
    data () {
      return {
        msg: '',
      }
    },
    mounted() {
    /* fetch("/api/serverinfo")
      .then((response) => response.text())
      .then((data) => {
          this.msg = data;
      }); */

      axios.get('/api/serverinfo')
      .then((response) => {
        this.msg = response.data;
      }).catch((error) => {
        this.msg = error.toJSON();
      });
  },
  }
</script>
