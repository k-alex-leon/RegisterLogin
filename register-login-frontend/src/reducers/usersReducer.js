export const usersReducer = (state = [], action) => {
  switch (action.type) {
    // cada case representa una accion a realizar con los objetos
    case "addUser":
      return [
        ...state,
        {
          // agrega el nuevo usuario
          ...action.payload,
        },
      ];

    case "updateUser":
      return state.map((u) => {
        if (u.id === action.payload.id) {
          return {
            ...action.payload
          };
        }
        return u;
      });

    case "removeUser":
      // retorna una nueva lista sin el usuario seleccionado
      return state.filter((user) => user.id !== action.payload);

    case "loadingUsers":
      return action.payload

    default:
      return state;
  }
};
