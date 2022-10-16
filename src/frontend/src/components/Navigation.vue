<template>
  <v-navigation-drawer
   v-model="drawer"
   color="blue-grey"
   dark="dark" 
   app
   >
    <v-list>
      <v-list-item class="px-2">
        <v-list-item-avatar>
          <v-icon large color="purple darken-2">mdi-dialpad</v-icon>
        </v-list-item-avatar>
      </v-list-item>

      <v-list-item link>
        <v-list-item-content>
          <v-list-item-title class="text-h6">
            {{ username || "Username" }}
          </v-list-item-title>

          <v-list-item-subtitle v-on:click="logout">
            log out
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-divider></v-divider>

    <v-list dense nav>
      <v-list-item
        v-for="item in items"
        :key="item.title"
        :to="item.to ? item.to : null"
      >
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  props: {
    drawer: null,
  },
  data() {
    return {
      dark: true,
      items: [
        { title: "Dashboard", icon: "mdi-view-dashboard", to: "/dashboard" },
        { title: "User", icon: "mdi-account", to: "/user" },
        { title: "Admin", icon: "mdi-account-cowboy-hat", to: "/admin" },
        { title: "Test", icon: "mdi-ab-testing", to: "/test" },
      ],
      username: this.$route.params.username,
    };
  },
  methods: {
    logout() {
      this.$router.replace({ name: "login" });
    },
  },
};
</script>
