import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/admin">
        Admin
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        Comment
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        Customer
      </MenuItem>
      <MenuItem icon="asterisk" to="/evaluation">
        Evaluation
      </MenuItem>
      <MenuItem icon="asterisk" to="/favorites">
        Favorites
      </MenuItem>
      <MenuItem icon="asterisk" to="/ingredient">
        Ingredient
      </MenuItem>
      <MenuItem icon="asterisk" to="/ingredient-of-dish">
        Ingredient Of Dish
      </MenuItem>
      <MenuItem icon="asterisk" to="/ingredient-type">
        Ingredient Type
      </MenuItem>
      <MenuItem icon="asterisk" to="/post">
        Post
      </MenuItem>
      <MenuItem icon="asterisk" to="/profession">
        Profession
      </MenuItem>
      <MenuItem icon="asterisk" to="/step-to-cook">
        Step To Cook
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
