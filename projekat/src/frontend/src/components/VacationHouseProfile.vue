<template>
  <div class="main-container">
    <div style="display: flex">
      <div style="float: left; width: 70%">
        <h3 class="main-heading">{{vacationHouse.name}}</h3>
        <vue3-star-ratings class="star-ratings" v-model="vacationHouse.rating" starSize="22"  :showControl=false :disableClick=true :step=0 />
        <h5 class="star-heading">({{vacationHouse.reviewsNumber}})</h5>
      </div>
      <div style="width: 30%; margin-top: 20px">
        <div style="float: right; display: flex; overflow: hidden;">
          <button style="margin-right: 20px" type="button" @click="toggleEdit()" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modal">Edit info</button>
          <button type="button" @click="toggleCreatingOffer()" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalOffer">Create offer</button>
        </div>
      </div>
    </div>
    <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img class="d-block w-100" alt="Active picture" v-bind:src="'data:image/jpeg;base64,' + pictures.at(0)">
        </div>
        <div class="carousel-item" v-for="(picture, index) in pictures.slice(1)" :key="index">
          <img class="d-block w-100" alt="Item picture" v-bind:src="'data:image/jpeg;base64,' + picture">
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
    <div class="main-description">
      <div class="inner-description">
        <div><b>Address: </b> {{vacationHouse.address}}</div>
        <div><b>Description: </b> {{vacationHouse.promoDescription}}</div>
        <div><b>Behaviour rules: </b> {{vacationHouse.behaviourRules}}</div>
        <div><b>Price: </b> {{vacationHouse.priceList}} per day</div>
        <div><b>Additional info: </b>{{vacationHouse.additionalInfo}}</div>
        <div><b>Cancellation conditions: </b>{{vacationHouse.cancellationConditions}}</div>
        <div><b>Capacity: </b>{{vacationHouse.roomsQuantity}} rooms, {{vacationHouse.bedsPerRoom}} beds per room</div>
        <div><b>Availability:</b> From {{vacationHouse.availableFrom}} to {{vacationHouse.availableTo}}</div>
      </div>
      <div class="google-map-container">
        <iframe class="google-map" v-bind:src="'https://maps.google.com/maps?q=' + vacationHouse.address + '&t=&z=13&ie=UTF8&iwloc=&output=embed'"></iframe>
      </div>
    </div>
    <hr>
  </div>
  <div class="modal fade" id="modal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input v-model="vacationHouse.name" type="text" class="form-control" id="floatName" placeholder="Title">
            <label for="floatName">Title</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="vacationHouse.address" type="text" class="form-control" id="floatAddress" placeholder="Address">
            <label for="floatAddress">Address</label>
          </div>
          <div class="form-floating mb-3">
            <textarea style="height: 100px" v-model="vacationHouse.promoDescription" type="text" class="form-control" id="floatPromoDescription" placeholder="Description"></textarea>
            <label for="floatPromoDescription">Description</label>
          </div>
          <div class="form-floating mb-3">
            <textarea style="height: 100px" v-model="vacationHouse.behaviourRules" type="text" class="form-control" id="floatBehaviourRules" placeholder="Behaviour rules"></textarea>
            <label for="floatBehaviourRules">Behaviour rules</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="vacationHouse.priceList" type="text" class="form-control" id="floatPriceList" placeholder="Price">
            <label for="floatPriceList">Price</label>
          </div>
          <div class="form-floating mb-3">
            <textarea style="height: 100px" v-model="vacationHouse.additionalInfo" type="text" class="form-control" id="floatAdditionalInfo" placeholder="Additional info"></textarea>
            <label for="floatAdditionalInfo">Additional info</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="vacationHouse.cancellationConditions" type="text" class="form-control" id="floatCancellationConditions" placeholder="Cancellation conditions">
            <label for="floatCancellationConditions">Cancellation conditions</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="vacationHouse.roomsQuantity" type="text" class="form-control" id="floatRoomsQuantity" placeholder="Rooms quantity">
            <label for="floatRoomsQuantity">Rooms quantity</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="vacationHouse.bedsPerRoom" type="text" class="form-control" id="floatBedsPerRoom" placeholder="Beds per room">
            <label for="floatBedsPerRoom">Beds per room</label>
          </div>
          <Datepicker v-model="availabilityInterval" :format="formatRange" range/>
        </div>
        <div class="modal-footer">
          <button type="button" @click="cancel()" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
          <button type="button" @click="saveEdit()" class="custom-btn button-primary" data-bs-dismiss="modal">Save changes</button>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="modalOffer" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input v-model="offer.place" type="text" class="form-control" id="floatPlace" placeholder="Place">
            <label for="floatPlace">Place</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="offer.clientLimit" type="text" class="form-control" id="floatClientLimit" placeholder="Client limit">
            <label for="floatClientLimit">Client limit</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="offer.price" type="text" class="form-control" id="floatPrice" placeholder="Price">
            <label for="floatPrice">Price</label>
          </div>
          <Datepicker v-model="offerInterval" :format="formatRange" range/>
        </div>
        <div class="modal-footer">
          <button type="button" @click="cancel()" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
          <button type="button" @click="createOffer()" class="custom-btn button-primary" data-bs-dismiss="modal">Save changes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useRoute } from 'vue-router';
import {getCurrentInstance, onMounted, ref} from 'vue';
import moment from "moment";

export default {
  name: "VacationHouseProfile",
  setup() {
    const route = useRoute();
    const toast = getCurrentInstance().appContext.config.globalProperties.$toast;

    let id = ref('3');
    let vacationHouse = ref({
      id: null,
      name: null,
      address: null,
      promoDescription: null,
      availableFrom: null,
      availableTo: null,
      behaviourRules: null,
      priceList: null,
      additionalInfo: null,
      cancellationConditions: null,
      roomsQuantity: null,
      bedsPerRoom: null
    });
    let offer = ref({
      id: null,
      place: null,
      clientLimit: null,
      additionalServices: [],
      price: null,
      rentingEntityId: null,
      start : null,
      duration: null
    });
    let pictures = ref([]);
    let editing = ref(false);
    let creatingOffer = ref(false);
    let availabilityInterval = ref();
    let offerInterval = ref();

    onMounted(() => {
      if (route.params.id !== undefined) {
        id.value = route.params.id;
      }
      axios
          .get(`/api/vacation_houses/${id.value}`)
          .then(response => (vacationHouse.value = response.data));

      axios
          .get(`/api/vacation_houses/${id.value}/pictures/all`)
          .then(response => {
            for (let i = 0; i < response.data.length; i++) {
              pictures.value.push(response.data[i]);
            }
          });
    });

    const formatRange = (dates) => {
      let from = dates[0];
      let to = dates[1];

      const dayFrom = from.getDate();
      const monthFrom = from.getMonth() + 1;
      const yearFrom = from.getFullYear();
      const hourFrom = from.getHours();
      const minuteFrom = from.getMinutes();

      const dayTo = to.getDate();
      const monthTo = to.getMonth() + 1;
      const yearTo = to.getFullYear();
      const hourTo = to.getHours();
      const minuteTo = to.getMinutes();

      return `${dayFrom}.${monthFrom}.${yearFrom} ${hourFrom}:${minuteFrom} - ${dayTo}.${monthTo}.${yearTo} ${hourTo}:${minuteTo}`
    }

    function toggleEdit() {
      editing.value = true;
      let from = moment(vacationHouse.value.availableFrom, "dd.MM.yyyy HH:mm")
      let to = moment(vacationHouse.value.availableTo, "dd.MM.yyyy HH:mm")
      availabilityInterval.value = [from, to];
    }

    function toggleCreatingOffer() {
      creatingOffer.value = true;
    }

    function parseDate(date) {
      const day = ("0" + date.getDate()).slice(-2);
      const month = ("0" + (date.getMonth() + 1)).slice(-2);
      const year = date.getFullYear();
      const hours = ("0" + date.getHours()).slice(-2);
      const minutes = ("0" + date.getMinutes()).slice(-2);
      return `${day}.${month}.${year} ${hours}:${minutes}`;
    }

    function toastMessage() {
      toast.success("Info updated");
    }

    function saveEdit(){
      vacationHouse.value.availableFrom = parseDate(availabilityInterval.value[0]);
      vacationHouse.value.availableTo = parseDate(availabilityInterval.value[1]);

      axios
          .put(`/api/vacation_houses/${id.value}`, vacationHouse.value)
          .then(response => {
            vacationHouse.value = response.data;
            toastMessage();
          });

      editing.value = false;
    }

    function createOffer() {
      offer.value.start = parseDate(offerInterval.value[0]);
      offer.value.duration = offerInterval.value[1] - offerInterval.value[0];
      offer.value.rentingEntityId = id.value;

      axios
          .post("/api/offers/new", offer.value)
          .then(response => {
            offer.value = response.data;
            toastMessage();
          });

      creatingOffer.value = false;
    }

    function cancel() {
      editing.value = false;
      creatingOffer.value = false;
    }

    return {
      id,
      vacationHouse,
      offer,
      pictures,
      editing,
      creatingOffer,
      availabilityInterval,
      offerInterval,
      formatRange,
      toggleEdit,
      toggleCreatingOffer,
      parseDate,
      toastMessage,
      saveEdit,
      createOffer,
      cancel
    }
  }
}

</script>

<style scoped>

.carousel-inner {
  border-radius: 5px;
  height: 500px;
}

div.star-ratings {
  padding: 0;
  margin: 0;
  float: left;
}

.main-container {
  width: 60%;
  margin:auto;
}

.main-heading {
  font-weight: bold;
  margin: 10px 0 0 0;
}

.star-ratings {
  margin-right: 5px;
}

.star-heading {
  margin-top: 3px;
}

.main-description {
  display:flex;
  margin-top: 17px;
}

.inner-description {
  margin-right: 50px;
  float:left;
  width: 65%;
  overflow: hidden;
}

.google-map-container {
  display: flex;
  float: right;
  width: 35%;
  overflow: hidden;
}

.google-map {
  width: 400px;
  border-radius: 5px;
}

</style>