<template>
  <v-data-table
    :page="page"
    :pageCount="numberOfPages"
    :headers="headers"
    :items="users"
    :options.sync="options"
    :server-items-length="totalUsers"
    :loading="loading"
    :search="search"
    sort-by="userName"
    class="elevation-1"
  >
    <template v-slot:top>
      <Toast ref="toast" />
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
            <v-form
    ref="form"
    v-model="valid"
    lazy-validation
  >
            <v-card-title v-card-title class="text-h5 grey lighten-2">
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  
                  <v-col cols="12" sm="6" md="12">
                    <v-select
                      :items="userRoles"
                      item-text="name"
                      item-value="id"
                      v-model="selectedRole"
                    ></v-select>
                  </v-col>
                  <v-col cols="12" sm="6" md="12">
                    <v-text-field
                      v-model="editedItem.userName"
                      :rules="userNameRules"
                      label="User name"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="12">
                    <v-text-field
                      v-model="editedItem.password"
                      label="Password"
                      :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                      :type="show1 ? 'text' : 'password'"
                      :rules="[passwordRules.uppercaseEn, passwordRules.special, passwordRules.number, passwordRules.length]"
                      @click:append="show1 = !show1"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="12">
                    <v-text-field 
                      :readonly="readOnlyEmail"
                      v-model="editedItem.email"
                      :rules="emailRules"
                      label="Email"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col v-if="isEditDialogue" cols="12" sm="6" md="12">
                    <v-text-field
                     :readonly="true"
                      v-model="editedItem.id"
                      label="Id"
                    ></v-text-field>
                  </v-col>
                  <v-col v-if="isEditDialogue" cols="12" sm="6" md="12">
                    <v-text-field
                      :readonly="true"
                      v-model="editedItem.token"
                      append-icon="mdi-content-copy"
                      ref="userToken"
                      @click:append="copyText"
                      label="Token"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">
                Cancel
              </v-btn>
              <v-btn :disabled="!valid" color="blue darken-1" text @click="save">
                Save
              </v-btn>
            </v-card-actions>
            </v-form>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5"
              >Are you sure you want to delete this item? user:{{ editedItem.userName }}</v-card-title
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
import helperMixin from "@/mixins/helperMixin";
import Toast from '@/components/Toast.vue';
export default {
  components: {
    Toast
  },
  mixins: [helperMixin],
  data: () => ({
    valid: true,
    userNameRules: [
        v => !!v || 'User name is required',
        v => (v && v.length > 3 && v.length <= 16) || 'Name must atleast 4 characters and must be less than 16 characters',
      ],
    emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      ],
      passwordRules: {
      uppercase: value => {
        //For non-latin letters also
        return (value && value.toLowerCase() !== value) || 'At least 1 uppercase letter'
      },
      uppercaseEn: value => {
        //For latin letters only
        return /[A-Z]/.test(value) || 'At least 1 uppercase letter (A-Z only)'
      },
      number: value => {
        return /\d/.test(value) || 'At least 1 number'
      },
      special: value => {
        return /[!@#\$%\^&\*]/.test(value) || 'At least 1 special symbol'
      },
      length: value => {
        return (value && value.length >= 8) || 'Min password length: 8 symbols'
      }
    },
    show1: false, //password icon
    page: 1,
    selectedRole: "USER",
    userRoles: [
      { id: "SUPER_ADMIN", name: "SUPER ADMIN"},
      { id: "ADMIN", name: "ADMIN"},
      { id: "USER", name: "USER"}
      ],
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
      //{text: "ID", align: "start", sortable: false, value: "id",},
      { text: "User Name", value: "userName" },
      { text: "Password", value: "password" },
      { text: "Email", value: "email" },
      { text: "Role", value: "role" },
      //{ text: "Token", value: "token" },
      { text: "Created At", value: "createdAt", sortable: false },
      { text: "Updated At", value: "updatedAt", sortable: false },
      { text: "Actions", value: "actions", sortable: false },
    ],
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
    readOnlyEmail() {
      return this.editedIndex === -1 ? false : true;
    },
    isEditDialogue() {
      return this.editedIndex === -1 ? false : true;
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
    //alert(this.timeNow());
  },

  //this will trigger in the onReady State
  mounted() {
    this.readDataFromAPI();
  },

  methods: {

    validate () {
        this.$refs.form.validate()
      },
      reset () {
        this.$refs.form.reset()
      },
      resetValidation () {
        this.$refs.form.resetValidation()
      },

    copyText() {
      const input = this.$refs.userToken.$refs.input
      input.select()
      document.execCommand('copy')
      input.setSelectionRange(0,0) // unselect
      
      alert('text copied: \n' + input.value)
      //this.$refs.toast.show('text copied', input.value, 10); TODO FIX
    },
    
    initialize() {
      //this.readDataFromAPI();
    },

    goToFirstPage() {
      this.$set(this.options, 'page', 1);
      this.readDataFromAPI();
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
      //this.users.splice(this.editedIndex, 1);
      this.deleteUser();
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
        this.updateUser();
      } else {
        this.saveUser();
      }
      
      this.close();
    },
    
    //Reading data from API method.
    readDataFromAPI() {
      this.loading = true;
      const { page, itemsPerPage } = this.options;
      let pageNumber = page - 1;
      axios
        .get(
          "http://localhost:8989/api/users/paged?size=" +
            itemsPerPage +
            "&page=" +
            pageNumber
        )
        .then((response) => {
          //Then injecting the result to datatable parameters.
          //console.log(response.data);
          this.loading = false;
          this.users = response.data.data;
          this.totalUsers = response.data.totalItems;
          this.numberOfPages = response.data.totalPages;
          //this.page = response.data.currentPage;
        });
    },

    // save user
    saveUser() {
      axios
        .post("http://localhost:8989/api/users", {
          userName: this.editedItem.userName,
          password: this.editedItem.password,
          email: this.editedItem.email,
          role: this.selectedRole,
        })
        .then((response) => {
          this.goToFirstPage();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // update user
    updateUser() {
      axios
        .put("http://localhost:8989/api/users/"+this.editedItem.id, {
          userName: this.editedItem.userName,
          password: this.editedItem.password,
          email: this.editedItem.email,
          role: this.selectedRole,
        })
        .then((response) =>{
          this.goToFirstPage();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // delete user
    deleteUser() {
      axios
        .delete("http://localhost:8989/api/users/"+this.editedItem.id)
        .then((response) => {
          this.goToFirstPage();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
.v-textarea textarea[readonly="readonly"] {
    background-color: #f0f0f0;
    color: gray;
}
:not(.v-select).v-text-field input[readonly="readonly"] {
    background-color: #f0f0f0;
    color: gray;
}
</style>
