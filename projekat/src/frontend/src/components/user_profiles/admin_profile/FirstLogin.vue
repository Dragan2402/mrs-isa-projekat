<template>
  <div style="text-align: center; margin: 15px">
    <h4>Hello, please set a new password.</h4>
    <br>
  <div class="form-floating input-group-width mb-2" style="width: 300px">
    <input id="lastNameInput" class="form-control" type="password" v-model="newPassword" placeholder="New password">
    <label for="lastNameInput">New password</label>
  </div>
  <div class="form-floating input-group-width mb-2" style="width: 300px">
    <input id="lastNameInput" class="form-control" type="password" v-model="confirmPassword" placeholder="Confirm new password">
    <label for="lastNameInput">Confirm new password</label>
  </div>
  <button class="custom-btn button-primary mt-2" @click="changePassword">Change</button>
  </div>
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
      axios.put(`https://renting-buddy-spring.herokuapp.com/api/admins/changePassword`, passwordChange, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
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