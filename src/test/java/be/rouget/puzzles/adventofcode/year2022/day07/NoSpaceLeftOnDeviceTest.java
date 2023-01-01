package be.rouget.puzzles.adventofcode.year2022.day07;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NoSpaceLeftOnDeviceTest {

    @Test
    void buildFileSystem() {
        List<String> input = List.of(
                "$ cd /", 
                        "$ ls", 
                        "dir a", 
                        "14848514 b.txt", 
                        "8504156 c.dat", 
                        "dir d", 
                        "$ cd a", 
                        "$ ls", 
                        "dir e", 
                        "29116 f", 
                        "2557 g", 
                        "62596 h.lst", 
                        "$ cd e", 
                        "$ ls", 
                        "584 i", 
                        "$ cd ..", 
                        "$ cd ..", 
                        "$ cd d", 
                        "$ ls", 
                        "4060174 j", 
                        "8033020 d.log", 
                        "5626152 d.ext", 
                        "7214296 k"
                );
        DiskDirectory actual = NoSpaceLeftOnDevice.buildFileSystem(input);
        
        DiskDirectory expected = new DiskDirectory("/", List.of(
                new DiskDirectory("a", List.of(
                        new DiskDirectory("e", List.of(
                                new DiskFile("i", 584L)
                        )),
                        new DiskFile("f", 29116L),
                        new DiskFile("g", 2557L),
                        new DiskFile("h.lst", 62596L)
                )),
                new DiskFile("b.txt", 14848514L),
                new DiskFile("c.dat", 8504156L),
                new DiskDirectory("d", List.of(
                        new DiskFile("j", 4060174L),
                        new DiskFile("d.log", 8033020L),
                        new DiskFile("d.ext", 5626152L),
                        new DiskFile("k", 7214296L)
                ))
        ));

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

        assertThat(actual.size()).isEqualTo(48381165L);
    }
}