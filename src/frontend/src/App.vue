<template>
<v-app>
  <Navigation v-if="isShowavigation" :drawer="drawer"/>
  <v-app-bar app color="deep-purple" dark>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title>Autolink token server</v-toolbar-title>
    </v-app-bar>
  <v-main>

    <!-- Provides the application the proper gutter -->
    <v-container fluid>

      <!-- If using vue-router -->
      <router-view></router-view>
    </v-container>
  </v-main>

  <Footer />
</v-app>
</template>
<script>
import Footer from '@/components/Footer.vue'
import Navigation from '@/components/Navigation.vue'
export default {
  name: "App",
  components: {
    Footer,
    Navigation,
  },
  data: () => ({ drawer: null }),
  computed: {
    isShowavigation() { //do not show navigation on login
        return this.$route.name != "login";
    }
  },
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
