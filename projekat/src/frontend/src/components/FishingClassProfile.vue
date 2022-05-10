<template>
  <head>
    <title>Fishing Class Profile</title>
  </head>
  <body>
  <div class="page">
    <div v-if="!this.editing" class="user-data-div">
      <p>Name: {{ fishingClass.name }}</p>
      <p>Address: {{ fishingClass.address }}</p>
      <p>Description: {{ fishingClass.promoDescription }}</p>
      <p>Instructor biography: {{ fishingClass.instructorBiography }}</p>
      <p>Behaviour Rules: {{ fishingClass.behaviourRules }}</p>
      <p>Price list: {{ fishingClass.priceList }}</p>
      <p>Client limit: {{ fishingClass.clientLimit }}</p>
      <p>Additional info: {{ fishingClass.additionalInfo }} </p>
      <p>Cancellation conditions: {{ fishingClass.cancellationConditions }}</p>
      <p>Available from: {{ fishingClass.availableFrom }}</p>
      <p>Available to: {{ fishingClass.availableTo }}</p>
    </div>
    <div v-else class="user-data-div">
      <div class="input-group mb-3">
        <span class="input-group-text" id="name">Name:</span>
        <input v-model="fishingClass.name" type="text" class="form-control" aria-label="Username" aria-describedby="name">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="address">Address:</span>
        <input v-model="fishingClass.address" type="text" class="form-control" aria-label="Username" aria-describedby="address">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="promoDescription">Description:</span>
        <input v-model="fishingClass.promoDescription" type="text" class="form-control" aria-label="Username" aria-describedby="promoDescription">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="instructorBiography">Instructor biography:</span>
        <input v-model="fishingClass.instructorBiography" type="text" class="form-control" aria-label="Username" aria-describedby="instructorBiography">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="behaviourRules">Behaviour Rules:</span>
        <input v-model="fishingClass.behaviourRules" type="text" class="form-control" aria-label="Username" aria-describedby="behaviourRules">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="priceList">Price list:</span>
        <input v-model="fishingClass.priceList" type="text" class="form-control" aria-label="Username" aria-describedby="priceList">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="clientLimit">Client limit: </span>
        <input v-model="fishingClass.clientLimit" type="text" class="form-control" aria-label="Username" aria-describedby="clientLimit">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="additionalInfo">Additional info:</span>
        <input v-model="fishingClass.additionalInfo" type="text" class="form-control" aria-label="Username" aria-describedby="additionalInfo">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="cancellationConditions">Cancellation conditions:</span>
        <input v-model="fishingClass.cancellationConditions" type="text" class="form-control" aria-label="Username" aria-describedby="cancellationConditions">
      </div>
      <div v-if="this.editing">
        <Datepicker placeholder="From" style="min-width: 100%" :format ='format' v-model="dateFrom" vertical />
        <br>
        <Datepicker placeholder="To" style="min-width: 100%" :format ='format' v-model="dateTo" vertical />
      </div>
    </div>
    <br>
    <button v-if="!this.editing" @click="toggleEdit()" class="btn btn-primary">Edit Info</button>
    <div v-else>
      <button style="margin-right: 20px" class="btn btn-primary" @click="saveEdit()">Save</button>
    </div>


  </div>
  </body>
</template>

<script>
import axios from "axios"

export default {
  name: "FishingClassProfile",
  data() {
    return {
      fishingClass: {},
      editing: false,
      dateFrom: null,
      dateTo: null
    }
  },
  mounted() {
    axios
        .get("/api/fishingClasses/2")
        .then(response => (this.fishingClass = response.data));
  },
  methods: {
    toggleEdit() {
      this.editing = true;
    },

    parseDate(date) {
      const day = ("0" + date.getDate()).slice(-2);
      const month = ("0" + (date.getMonth() + 1)).slice(-2);
      const year = date.getFullYear();
      const hours = ("0" + date.getHours()).slice(-2);
      const minutes = ("0" + date.getMinutes()).slice(-2);
      return `${day}.${month}.${year} ${hours}:${minutes}`;
    },
    saveEdit() {
      this.fishingClass.availableFrom = this.parseDate(this.dateFrom);
      this.fishingClass.availableTo = this.parseDate(this.dateTo);
      axios.put("/api/fishingClasses/update", this.fishingClass).then(response => this.fishingClass = response.data).then(this.toastMessage());
      this.editing = false;
    },
    toastMessage() {
      this.$toast.success("Info updated");
    }
  }
}
</script>

<style scoped>

</style>