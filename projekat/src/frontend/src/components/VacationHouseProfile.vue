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
      editing: false
    }
  },
  mounted() {
    axios
      .get('/api/vacation_houses/3')
      .then(response => (this.vacationHouse = response.data));
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

<style>

.page{
  background-color: beige;
  display: flex;
  flex-direction: column;
  font-family: 'Poppins', sans-serif;
  align-items: center;
}

.edit-button{
  background-color: white;
  height: 5%;
  width: 8%;
  text-align-last: center;
}

.user-data-div{

  margin: 20px;
  border: 0.01px solid;

  display: flex;
  justify-content: center;
  background-color: white;

  flex-direction: column;
  align-items: center;
  padding: 50px;
}

</style>