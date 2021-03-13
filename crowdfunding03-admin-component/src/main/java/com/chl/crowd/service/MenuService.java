package com.chl.crowd.service;

import com.chl.crowd.entity.Menu;

public interface MenuService {
    public Menu getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
