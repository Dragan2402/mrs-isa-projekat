<template>
  <div class="page">
    <div v-if="!this.editing" class="user-data-div">
      <h1>{{vacationHouse.name}} </h1>
      <p>Address: {{vacationHouse.address}}</p>
      <p>Description: {{vacationHouse.promoDescription}}</p>
      <p>Behavior rules: {{vacationHouse.behaviourRules}}</p>
      <p>Price list: {{vacationHouse.priceList}}</p>
      <p>Additional info: {{vacationHouse.additionalInfo}}</p>
      <p>Cancellation conditions: {{vacationHouse.cancellationConditions}}</p>
      <p>Rooms: {{vacationHouse.roomsQuantity}}</p>
      <p>Beds per room: {{vacationHouse.bedsPerRoom}}</p>
    </div>
    <div v-else class="user-data-div">
      <p>Name: <input type="text" v-model="vacationHouse.name"></p>
      <p>Address: <input type="text" v-model="vacationHouse.address"></p>
      <p>Description: <input type="text" v-model="vacationHouse.promoDescription"></p>
      <p>Behavior rules: <input type="text" v-model="vacationHouse.behaviourRules"></p>
      <p>Price list: <input type="text" v-model="vacationHouse.priceList"></p>
      <p>Additional info: <input type="text" v-model="vacationHouse.additionalInfo"></p>
      <p>Cancellation conditions: <input type="text" v-model="vacationHouse.cancellationConditions"></p>
      <p>Rooms: <input type="text" v-model="vacationHouse.roomsQuantity"></p>
      <p>Beds per room: <input type="text" v-model="vacationHouse.bedsPerRoom"></p>
    </div>
    <button v-if="!this.editing" @click="toggleEdit()" class="edit-button">Edit Info</button>
    <button v-else @click="saveEdit()" class="edit-button">Save</button>
  </div>

<!--  <div v-for="(picture, index) in pictures" :key="index">-->
<!--    <img v-bind:src="'data:image/jpeg;base64,' + picture" width="640" height="480">-->
<!--  </div>-->

  <div class="container">
    <div class="row row-cols-4">
      <div class="col p-3" v-for="(picture, index) in pictures" :key="index">
        <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 100%; height: auto;">
      </div>
    </div>
  </div>

</template>

<script>
import axios from "axios"

export default {
  name: "VacationHouseProfile",
  data() {
    return {
      vacationHouse: {
        id: null,
        name: null,
        address: null,
        promoDescription: null,
        behaviourRules: null,
        priceList: null,
        additionalInfo: null,
        cancellationConditions: null,
        roomsQuantity: null,
        bedsPerRoom: null
      },
      pictures: [],
      editing: false
    }
  },
  mounted() {
    axios
      .get('/api/vacation_houses/3')
      .then(response => (this.vacationHouse = response.data));
    axios
      .get('/api/vacation_houses/3/pictures/all')
      .then(response => {
        for (let i = 0; i < response.data.length; i++) {
          this.pictures.push(response.data[i]);
        }
      });
    axios
      .get('/api/vacation_houses/3/')
  },
  methods: {
    toggleEdit() {
      this.editing = true;
    },
    saveEdit(){
      axios
          .put("/api/vacation_houses/3", this.vacationHouse)
          .then(response => {
            this.user = response.data;
            this.toastMessage();
          });
      this.editing = false;
    },
    toastMessage(){
      this.$toast.success("Info updated");
    }
  }
}
</script>

<style scoped>

</style>