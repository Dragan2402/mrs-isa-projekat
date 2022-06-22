<template>
  <div class="main-wrapper">
    <div class="for-container" v-for="(report, index) in this.reports" v-bind:index="index" :key="report.id">
      <div class="inner-container">
        <div class="left-container">
          <div><b>Submitter:</b> {{ report.submitterUsername }}</div>
          <div><b>Rate:</b> <span style="background-color: indianred; border-radius: 5px; padding: 0 5px">Bad</span></div>
          <div><b>Comment:</b> {{ report.text }}</div>
          <div><b>Client:</b> {{ report.clientUsername }}</div>
        </div>
        <div class="right-container">
          <button class="custom-btn button-primary" style="width: 120px" @click="approveReport(report, index)">Give penalty</button>
          <button class="custom-btn button-primary ms-5" style="width: 120px; background-color: indianred" @click="rejectReport(report, index)">Dismiss</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "AdminReports",
  data() {
    return {
      reports: []
    }
  },
  mounted() {
    axios.get("api/admins/reports/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.reports = response.data))
  },
  methods: {
    approveReport(report, index) {
      axios.put("api/admins/reports", report, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Approved!");
          this.reports.splice(index,1);
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },
    rejectReport(report, index) {
      axios.put("api/admins/reports/reject", report, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Rejected!");
          this.reports.splice(index,1);
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