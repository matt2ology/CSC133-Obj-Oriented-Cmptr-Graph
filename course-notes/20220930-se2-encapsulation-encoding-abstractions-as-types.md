# SE2 :: Encapsulation :: Encoding Abstractions as Types

Author: DPCSUS
Date: Jan 13, 2022
Source: [SE2 :: Encapsulation :: Encoding Abstractions as Types](https://www.youtube.com/watch?v=-_IKBGs6hJ4)

- [SE2 :: Encapsulation :: Encoding Abstractions as Types](#se2--encapsulation--encoding-abstractions-as-types)
  - [Learning Objectives](#learning-objectives)
  - [Encoding Abstractions as Types](#encoding-abstractions-as-types)
  - [Design Context](#design-context)
  - [Learning Objectives Learned](#learning-objectives-learned)

## Learning Objectives

1. Be able to explain the concepts of encapsulation and information hiding
2. Evaluate the quality of encapsulation realized by a single class
3. Know some of the programming language mechanisms and idioms that support
    encapsulation, and how to apply them
4. Understand the importance of immutability in software design and how to make
    Java classes immutable
5. Know about code style, be able to explain how it contributes to code quality
6. Be able to create and interpret Object Diagrams

## Encoding Abstractions as Types

| Idea                       | Examples                                                        |
| -------------------------- | --------------------------------------------------------------- |
| Concepts and Principles    | encapsulation, information hiding abstraction,                  |
|                            | immutability, interface, reference sharing, escaping references |
| Programming Mechanisms     | typing, enumerated types, scopes, access modifiers              |
| Design Techniques          | class definition, object diagrams,                              |
|                            | immutable wrappers, reference copying                           |
| Patterns and Anti-patterns | Primitive obsession, inappropriate intimacy                     |

## Design Context

- How can we efficiently represent some domain (real world) concept in code?
- Answering this question is design
- We encode abstraction as types
  - Recall that an abstraction is a representation that hides detail
  1. We define abstractions thar are necessary to represent a domain concept
  2. We want to do our best to map the representation in code to that of the
  domain concept
  3. We want to make sure that the representation is not coupled to its
  implementation
  4. We want to avoid corrupting variables as much as possible

## Learning Objectives Learned

1. Be able to explain the concepts of encapsulation and information hiding
2. Evaluate the quality of encapsulation realized by a single class
3. Know some of the programming language mechanisms and idioms that support
    encapsulation, and how to apply them
4. Understand the importance of immutability in software design and how to make
    Java classes immutable
5. Know about code style, be able to explain how it contributes to code quality
6. Be able to create and interpret Object Diagrams