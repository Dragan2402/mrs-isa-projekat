<template>
  <div class="form-floating input-group-width mb-2" style="width: 200px">
    <input id="lastNameInput" class="form-control" type="text" v-model="fee" placeholder="Reservation fee">
    <label for="lastNameInput">Reservation fee</label>
  </div>
  <button class="custom-btn button-primary mt-1" @click="saveFee">Save</button>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminFee",
  data() {
    return {
      fee: null
    }
  },
  mounted() {
    axios.get("api/admins/fee", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.fee = response.data))
  },
  methods: {
    saveFee() {
      const feeDTO = {id: 1, value: this.fee};
      axios.put("api/admins/fee", feeDTO, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Successfully saved!");
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },
  }
}
</script>

<style scoped>

</style>