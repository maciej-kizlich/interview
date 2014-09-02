package com.epam.library.repository;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import pl.maciejkizlich.interview.persistence.model.Tag;

import com.epam.library.AbstractTest;

/**
 * Created by Denis_Ivanchenko on 7/21/2014.
 */

public class TagRepositoryTest extends AbstractTest {

    @Test
    public void testGetById() {
        Tag tag = tagRepository.findById((long) 1);
        Assert.assertNotNull(tag);
        Assert.assertTrue(tag.getId() == 1);
    }

    @Test
    public void testFindByName() {
        String testTitle = "Bla Bla Romantic";
        Collection<Tag> tags = tagRepository.findByTitle(testTitle);
        assertNotNullAndNotZeroElements(tags);
        Assert.assertTrue("There are no such title in the collection! " + testTitle, isTagsContainTitle(tags, testTitle));
    }

    @Test
    public void testFindByRegexp() {
        String testTitle = "Bla Bla Romantic";
        String testRegExp = "Bla Bla%";
        Collection<Tag> tags = tagRepository.findTitleLike(testRegExp);
        assertNotNullAndNotZeroElements(tags);
        Assert.assertTrue("There are no such title in the collection! " + testRegExp, isTagsContainTitle(tags, testTitle));
    }

    @Test
    public void testFindAll() {
        Collection<Tag> tags = tagRepository.findAll();
        assertNotNullAndNotZeroElements(tags);
    }

    @Test
    public void testInsertTag() {
        String title = "NEW TAG";
        Tag tag = new Tag();
        tag.setTitle(title);
        Tag newTag = tagRepository.save(tag);
        Assert.assertNotNull(newTag);
        Assert.assertEquals(newTag.getTitle(), title);
        Assert.assertNotNull(newTag.getId());

    }


    private boolean isTagsContainTitle(Collection<Tag> tags, String title) {
        boolean isContains = false;
        for (Tag tag: tags) {
            if (tag.getTitle().equals(title)) {
                isContains = true;
            }
        }
        return isContains;
    }
}
