<template>
  <div class="main-wrapper">
    <div class="for-container" v-for="(request, index) in this.requests" v-bind:index="index" :key="request.id">
      <div class="inner-container">
        <div class="left-container">
          <div v-if="request.text"><b>Comment:</b> {{ request.text }}</div>
          <div><b>User:</b> {{ request.submitterUsername }}</div>
          <div v-if="request.type === 'DELETE_ACCOUNT'"><b>Type:</b> Delete Account</div>
          <div v-if="request.type === 'BECOME_VH_OWNER'"><b>Type:</b> Vacation House Owner</div>
          <div v-if="request.type === 'BECOME_SH_OWNER'"><b>Type:</b> Ship Owner</div>
          <div v-if="request.type === 'BECOME_INSTRUCTOR'"><b>Type:</b> Fishing Instructor</div>

        </div>
        <div class="right-container">
          <button class="custom-btn button-primary" style="width: 120px" @click="approveRequest(request, index)">Approve</button>
          <button class="custom-btn button-primary ms-5" style="width: 120px; background-color: indianred" @click="approveRequest(request, index)">Reject</button>
        </div>
      </div>
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
          this.$toast.success("Success!");
          this.requests.splice(index, 1);
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    }
  }
}
</script>

<style scoped>

.main-wrapper {
  margin-bottom: 20%;
}

.for-container {
  display: flex;
  justify-content: center;
  margin-top: 15px
}

.inner-container {
  border: 1px solid #00587a;
  display: flex;
  padding: 10px;
  border-radius: 5px
}

.left-container {
  text-align: left;
  width: 300px
}

.right-container {
  display: flex;
  margin: auto 100px auto 200px
}

</style>