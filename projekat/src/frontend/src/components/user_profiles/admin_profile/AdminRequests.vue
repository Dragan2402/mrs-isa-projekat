<template>
  <div style="border: 1px solid black; margin: 10px; padding: 10px" v-for="(request, index) in this.requests"
       v-bind:index="index" :key="request.id">
    <div>Text: {{ request.text }}</div>
    <div>Type: {{ request.type }}</div>
    <div>
      <button class="custom-btn button-primary" @click="approveRequest(request, index)">Approve Request</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminRequests",
  data() {
    return {
      requests: []
    }
  },
  mounted() {
    axios.get("api/admins/requests/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.requests = response.data))
  },
  methods: {
    approveRequest(request, index) {
      axios.put("api/admins/requests", request, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Deleted");
          this.requests[index].deleted = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    }
  }
}
</script>

<style scoped>

</style>