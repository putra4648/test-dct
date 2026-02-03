import type { Router } from "vue-router";
import api from "./api";

export async function GetGroups() {
  const result = await api.get("http://localhost:8080/api/groups");
  return result.data as Group[];
}

export async function AddGroup(body: Group, router: Router) {
  api.post("http://localhost:8080/api/groups", body).then(() => {
    setTimeout(() => {
      router.push("/");
    }, 1000);
  });
}

export type Group = {
  id: number;
  name: string;
};
