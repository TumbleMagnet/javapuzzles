package be.rouget.puzzles.adventofcode.year2022.day07;

import com.google.common.collect.Lists;

import java.util.List;

public record DiskDirectory(String name, List<DiskItem> children) implements DiskItem {

    @Override
    public long size() {
        return children.stream()
                .mapToLong(DiskItem::size)
                .sum();
    }
    
    public List<DiskDirectory> childrenRepositories() {
        return children().stream()
                .filter(DiskDirectory.class::isInstance)
                .map(DiskDirectory.class::cast)
                .toList();
    }

    public List<DiskDirectory> listAllDirectories() {
        List<DiskDirectory> allDirectories = Lists.newArrayList();
        allDirectories.add(this);
        for (DiskDirectory child : childrenRepositories()) {
            allDirectories.addAll(child.listAllDirectories());
        }
        return allDirectories;
    }
}
