<template>
  <h2>TU SMO JACI SMO</h2>
  <input v-model="newPassword" type="password" placeholder="New password">
  <input v-model="confirmPassword" type="password" placeholder="Confirm new password">
  <button class="btn btn-primary" @click="changePassword">Change</button>
</template>

<script>
import axios from "axios";

export default {
  name: "FirstLogin",
  data() {
    return {
      newPassword: '',
      confirmPassword: '',
    }
  },

  mounted() {
  },

  methods: {
    changePassword() {
      var passwordRegex = /^[0-9A-Za-z]+$/;

      if (this.newPassword.length < 5 || this.newPassword.length > 20) {
        this.$toast.error("Password must be between 5-20 characters");
        return;
      }
      if (!passwordRegex.test(this.newPassword)) {
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      if (this.newPassword !== this.confirmPassword) {
        this.$toast.error("Passwords must match");
        return;
      }

      const passwordChange = {"oldPassword": null, "newPassword": this.newPassword, "newPasswordConfirm": this.confirmPassword};
      console.log(passwordChange);
      axios.put(`/api/admins/changePassword`, passwordChange, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$router.push("/");
        } else {
          this.$toast.error("Error");
        }
      });

    },
  }
}
</script>

<style scoped>

</style>