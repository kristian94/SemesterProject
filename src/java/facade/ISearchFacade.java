/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.SearchEntity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kristian Nielsen
 */
public interface ISearchFacade {
    public SearchEntity addSearch(SearchEntity search);
    public SearchEntity getSearch(int searchID);
    public List<SearchEntity> getSearches();
    public List<SearchEntity> getSearchesByDate(Date date);
    public List<SearchEntity> getSearchesByOrigin(String IATA);
    public List<SearchEntity> getSearchesByDesination(String IATA);
}
