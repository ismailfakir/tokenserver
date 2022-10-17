export default {
  created() {
    this.greetings();
  },
  methods: {
    greetings() {
      console.log('Using helperMixin!');
    },
    timeNow() {
      const currentDate = new Date();
      return currentDate.toISOString();
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
        function (c) {
          var r = (dt + Math.random() * 16) % 16 | 0;
          dt = Math.floor(dt / 16);
          return (c == "x" ? r : (r & 0x3) | 0x8).toString(16);
        }
      );
      console.log(uuid);
      return uuid;
    },
  }
};