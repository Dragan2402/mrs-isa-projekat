<template>
  <div class="page">
    <div v-if="!editing && !creatingOffer" class="user-data-div">
      <h1>{{ship.name}}</h1>
      <p>Address: {{ship.address}}</p>
      <p>Description: {{ship.promoDescription}}</p>
      <p>Behavior rules: {{ship.behaviourRules}}</p>
      <p>Price list: {{ship.priceList}}</p>
      <p>Additional info: {{ship.additionalInfo}}</p>
      <p>Cancellation conditions: {{ship.cancellationConditions}}</p>
      <p>Type: {{ship.type}}</p>
      <p>Length: {{ship.length}}</p>
      <p>Engine number: {{ship.engineNumber}}</p>
      <p>Engine power: {{ship.enginePower}}</p>
      <p>Top speed: {{ship.topSpeed}}</p>
      <p>Client limit: {{ship.clientLimit}}</p>
      <p>Available from: {{ ship.availableFrom }}</p>
      <p>Available to: {{ ship.availableTo }}</p>

      <button class="btn btn-primary" style="margin-right: 20px" @click="toggleEdit()" >Edit Info</button>
      <button class="btn btn-primary" @click="toggleCreatingOffer()" >Create Offer</button>
    </div>

    <div v-if="editing" class="user-data-div">
      <div class="input-group mb-3">
        <span class="input-group-text" id="name">Name:</span>
        <input v-model="ship.name" type="text" class="form-control" aria-label="Username" aria-describedby="name">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="address">Address:</span>
        <input v-model="ship.address" type="text" class="form-control" aria-label="Username" aria-describedby="address">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="promoDescription">Description:</span>
        <input v-model="ship.promoDescription" type="text" class="form-control" aria-label="Username" aria-describedby="promoDescription">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="behaviourRules">Behaviour Rules:</span>
        <input v-model="ship.behaviourRules" type="text" class="form-control" aria-label="Username" aria-describedby="behaviourRules">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="priceList">Price list:</span>
        <input v-model="ship.priceList" type="text" class="form-control" aria-label="Username" aria-describedby="priceList">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="clientLimit">Client limit: </span>
        <input v-model="ship.clientLimit" type="text" class="form-control" aria-label="Username" aria-describedby="clientLimit">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="additionalInfo">Additional info:</span>
        <input v-model="ship.additionalInfo" type="text" class="form-control" aria-label="Username" aria-describedby="additionalInfo">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="cancellationConditions">Cancellation conditions:</span>
        <input v-model="ship.cancellationConditions" type="text" class="form-control" aria-label="Username" aria-describedby="cancellationConditions">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="type">Type:</span>
        <input v-model="ship.type" type="text" class="form-control" aria-label="Username" aria-describedby="type">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="length">Length:</span>
        <input v-model="ship.length" type="text" class="form-control" aria-label="Username" aria-describedby="length">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="engineNumber">Engine number:</span>
        <input v-model="ship.engineNumber" type="text" class="form-control" aria-label="Username" aria-describedby="engineNumber">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="enginePower">Engine power:</span>
        <input v-model="ship.enginePower" type="text" class="form-control" aria-label="Username" aria-describedby="enginePower">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="topSpeed">Top speed:</span>
        <input v-model="ship.topSpeed" type="text" class="form-control" aria-label="Username" aria-describedby="topSpeed">
      </div>
      <Datepicker v-model="availabilityInterval" :format="formatRange" range/>
      <br>
      <button @click="saveEdit()" style="margin-right: 20px;" class="btn btn-primary">Save</button>
      <button @click="cancel()" class="btn btn-primary">Cancel</button>
    </div>

    <div v-if="creatingOffer" class="user-data-div">
      <div class="input-group mb-3">
        <span class="input-group-text" id="clientLimitOffer">Client limit:</span>
        <input v-model="offer.clientLimit" type="text" class="form-control" aria-label="Username" aria-describedby="clientLimitOffer">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="priceOffer">Price:</span>
        <input v-model="offer.price" type="text" class="form-control" aria-label="Username" aria-describedby="priceOffer">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="additionalServicesOffer">Additional Services:</span>
        <vue3-tags-input :tags="offer.additionalServices" @on-tags-changed="updateTags" class="form-control" aria-label="Username" aria-describedby="additionalServicesOffer"/>
      </div>
      <Datepicker v-model="offerInterval" :format="formatRange" range/>
      <br>
      <button @click="createOffer()" style="margin-right: 20px;"  class="btn btn-primary">Save</button>
      <button @click="cancel()" class="btn btn-primary">Cancel</button>
    </div>
      <div class="row row-cols-4">
        <div class="col p-3" style="margin: auto" v-for="(picture, index) in pictures" :key="index">
          <img v-bind:src="'data:image/jpeg;base64,' + picture" v-bind:alt="index">
        </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import {useRoute} from "vue-router";
import {getCurrentInstance, onMounted, ref} from "vue";
import Vue3TagsInput from 'vue3-tags-input';

export default {
  name: "ShipProfile",
  components: {
    Vue3TagsInput
  },
  setup() {
    const route = useRoute();
    const toast = getCurrentInstance().appContext.config.globalProperties.$toast;

    let editing = ref(false);
    let creatingOffer = ref(false);

    let id = ref(1);
    let ship = ref({
      id: Number,
      name: String,
      address: String,
      promoDescription: String,
      availableFrom: String,
      availableTo: String,
      behaviourRules: String,
      priceList: String,
      additionalInfo: String,
      cancellationConditions: String,
      type: String,
      length: Number,
      engineNumber: String,
      enginePower: Number,
      topSpeed: Number,
      clientLimit: Number
    });
    let pictures = ref([]);
    let availabilityInterval = ref([]);

    let offer = ref({
      start: String,
      duration: Number,
      clientLimit: "",
      additionalServices: [],
      price: "",

      id: null,
      place: ship.value.address,
      rentingEntityId: ship.value.id
    });
    let offerInterval = ref([]);

    let tags = ref([]);

    onMounted(() => {
      if (route.params.id !== undefined) {
        id.value = route.params.id;
      }
      axios
          .get(`/api/ships/${id.value}`)
          .then(response => {
            ship.value = response.data;
            availabilityInterval.value = [stringToDateTime(ship.value.availableFrom),
                                          stringToDateTime(ship.value.availableTo)];
          });
      axios
          .get(`/api/ships/${id.value}/pictures/all`)
          .then(response => {
            for (let i = 0; i < response.data.length; i++) {
              pictures.value.push(response.data[i]);
            }
          });
    });

    function toggleEdit() {
      editing.value = true;
    }

    function toggleCreatingOffer() {
      creatingOffer.value = true;
    }

    function cancel() {
      editing.value = false;
      creatingOffer.value = false;
    }

    function saveEdit(){
      ship.value.availableFrom = dateTimeToString(availabilityInterval.value[0]);
      ship.value.availableTo = dateTimeToString(availabilityInterval.value[1]);

      axios
          .put(`/api/ships/${id.value}`, ship.value)
          .then(response => {
            ship.value = response.data;
            toast.success("Info updated");
          });

      editing.value = false;
    }

    function createOffer() {
      offer.value.start = dateTimeToString(offerInterval.value[0]);
      offer.value.duration = offerInterval.value[1] - offerInterval.value[0];
      offer.value.place = ship.value.address;
      offer.value.rentingEntityId = parseInt(id.value);

      axios
          .post("/api/offers/new", offer.value)
          .then(response => {
            offer.value = response.data;
            toast.success("Info updated");
          });

      creatingOffer.value = false;
    }

    function updateTags(tags) {
      offer.value.additionalServices = tags;
    }

    function stringToDateTime(s) {
      let tokens = s.split(" ");
      let date = tokens[0];
      let time = tokens[1];

      tokens = date.split(".");
      let day = parseInt(tokens[0]);
      let month = parseInt(tokens[1]) - 1;
      let year = parseInt(tokens[2]);

      tokens = time.split(":");
      let hour = parseInt(tokens[0]);
      let minute = parseInt(tokens[1]);

      return new Date(year, month, day, hour, minute);
    }

    function dateTimeToString(dt) {
      let day = ("0" + dt.getDate()).slice(-2);
      let month = ("0" + (dt.getMonth() + 1)).slice(-2);
      let year = dt.getFullYear();
      let hour = ("0" + dt.getHours()).slice(-2);
      let minute = ("0" + dt.getMinutes()).slice(-2);
      return `${day}.${month}.${year} ${hour}:${minute}`;
    }

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

    return {
      editing,
      creatingOffer,
      ship,
      pictures,
      availabilityInterval,
      offer,
      offerInterval,
      tags,
      toggleEdit,
      toggleCreatingOffer,
      cancel,
      saveEdit,
      createOffer,
      updateTags,
      formatRange
    }
  }
}
</script>

<style scoped>

img {
  object-fit: cover;
  width: 300px;
  height: 200px;
}

</style>