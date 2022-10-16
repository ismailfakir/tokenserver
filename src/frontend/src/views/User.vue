<template>
  <v-data-table
    :headers="headers"
    :items="users"
    :options.sync="options"
    :loading="loading"
    :search="search"
    sort-by="calories"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Users</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              Add New User
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.userName"
                      label="User name"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.password"
                      label="Password"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.email"
                      label="Email"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">
                Cancel
              </v-btn>
              <v-btn color="blue darken-1" text @click="save">
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5"
              >Are you sure you want to delete this item?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete"
                >Cancel</v-btn
              >
              <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >OK</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)">
        mdi-pencil
      </v-icon>
      <v-icon small @click="deleteItem(item)">
        mdi-delete
      </v-icon>
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize">
        Reset
      </v-btn>
    </template>
  </v-data-table>
</template>

<script>
import axios from "axios";
// https://dev.to/instantwebtoolsnet/datatables-with-vuejs-vuetify-pagination-rest-api-3jji
export default {
  data: () => ({
    page: 0,
    totalUsers: 0,
    numberOfPages: 0,
    users: [],
    loading: true,
    options: {},
    //
    dialog: false,
    dialogDelete: false,
    search: "",
    headers: [
      {
        text: "ID",
        align: "start",
        sortable: false,
        value: "id",
      },
      { text: "User Name", value: "userName" },
      { text: "Password", value: "password" },
      { text: "Email", value: "email" },
      { text: "Token", value: "token" },
      { text: "Created At", value: "createdAt", sortable: false },
      { text: "Updated At", value: "updatedAt", sortable: false },
      { text: "Actions", value: "actions", sortable: false },
    ],
    //users: [],
    editedIndex: -1,
    editedItem: {
      _id: "",
      userName: "",
      password: "",
      email: "",
      token: "",
      createdAt: "",
      updatedAt: "",
    },
    defaultItem: {
      _id: "",
      userName: "",
      password: "",
      email: "",
      token: "",
      createdAt: "",
      updatedAt: "",
    },
  }),

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New User" : "Edit User";
    },
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
    options: {
      handler() {
        this.readDataFromAPI();
      },
      deep: true,
    },
  },

  created() {
    //this.initialize();
  },

  //this will trigger in the onReady State
  mounted() {
    this.readDataFromAPI();
  },

  methods: {
    timeNow() {
      const currentDate = new Date();
      return currentDate.toISOString();
    },
    initialize() {
      /* this.users = [
        {
          _id: "1",
          userName: "user1",
          password: "password1",
          email: "email1@1.com",
          token: "token1",
          createdAt: "",
          updatedAt: "",
        },
        {
          _id: "2",
          userName: "",
          password: "",
          email: "",
          token: "",
          createdAt: "",
          updatedAt: "",
        },
      ]; */
    },

    editItem(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    deleteItemConfirm() {
      this.users.splice(this.editedIndex, 1);
      this.closeDelete();
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      if (this.editedIndex > -1) {
        /* this.editItem.createdAt = this.now;
        this.editItem.updatedAt = this.now;
        this.editItem._id = this.create_UUID();
        this.editItem.token = this.create_UUID(); */

        /* let newUser = {
          _id: this.create_UUID(),
          userName: this.editedItem.userName,
          password: this.editedItem.password,
          email: this.editedItem.email,
          token: this.generate_token(16),
          createdAt: this.timeNow(),
          updatedAt: this.timeNow(),
        }; */

        //Object.assign(this.users[this.editedIndex], newUser);
      } else {
        /* let newUser = {
          _id: this.create_UUID(),
          userName: this.editedItem.userName,
          password: this.editedItem.password,
          email: this.editedItem.email,
          token: this.generate_token(16),
          createdAt: this.timeNow(),
          updatedAt: this.timeNow(),
        }; */
        //this.users.push(newUser);
        this.saveUser();
      }
      this.close();
    },
    generate_token(length) {
      //edit the token allowed characters
      var a = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split(
        ""
      );
      var b = [];
      for (var i = 0; i < length; i++) {
        var j = (Math.random() * (a.length - 1)).toFixed(0);
        b[i] = a[j];
      }
      return b.join("");
    },
    create_UUID() {
      var dt = new Date().getTime();
      var uuid = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(
        /[xy]/g,
        function(c) {
          var r = (dt + Math.random() * 16) % 16 | 0;
          dt = Math.floor(dt / 16);
          return (c == "x" ? r : (r & 0x3) | 0x8).toString(16);
        }
      );
      console.log(uuid);
      return uuid;
    },
    //Reading data from API method.
    readDataFromAPI() {
      this.loading = true;
      const { page, itemsPerPage } = this.options;
      let pageNumber = page - 1;
      axios
        .get(
          "http://localhost:8989/api/users?size=" +
            itemsPerPage +
            "&page=" +
            pageNumber
        )
        .then((response) => {
          //Then injecting the result to datatable parameters.
          console.log(response.data);
          this.loading = false;
          this.users = response.data;
          this.totalUsers = response.data.totalUsers;
          this.numberOfPages = response.data.totalPages;
        });
    },

    // save user
    saveUser() {
      axios
        .post("http://localhost:8989/api/users", {
          userName: this.editedItem.userName,
          password: this.editedItem.password,
          email: this.editedItem.email,
        })
        .then(function(response) {
          console.log(response);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
  },
};
</script>
