% ==========================================================================
% main_project6.m  -  Project #6: twodSFilter & medianSFilter demo
% Disciplina: Processamento Digital de Imagens - UFAM (2026)
% Author    : Pedro Victor dos Santos Oliveira
%
% Description:
%   Loads Fig3.37(a).jpg, applies:
%     i.  Averaging (low-pass) spatial filter with kernels 3x3, 11x11, 21x21
%         using twodSFilter().
%     ii. Median filter with 3x3 neighborhood using medianSFilter().
%   Displays all results side-by-side and saves output images.
% ==========================================================================

clear; clc; close all;

% Add Project #4 path (imagePad4e dependency)
addpath('../');

%% 1. Load input image
imgFile = '../Fig3.37(a).jpg';
if ~isfile(imgFile)
    error('Fig3.37(a).jpg not found.');
end
f = imread(imgFile);
fprintf('Loaded: %s  [%dx%d]\n', imgFile, size(f,1), size(f,2));

%% 2. Averaging filter - three kernel sizes
kernels = {3, 11, 21};
labels  = {'3x3', '11x11', '21x21'};
results_avg = cell(1,3);
for k = 1:3
    n = kernels{k};
    w = ones(n, n);           % box kernel (normalised inside twodSFilter)
    results_avg{k} = twodSFilter(f, w);
    fprintf('Averaging %s done.\n', labels{k});
end

%% 3. Median filter - 3x3
g_median = medianSFilter(f, 3);
fprintf('Median 3x3 done.\n');

%% 4. Display - averaging comparison
figure('Name','Averaging Filter - Project #6','NumberTitle','off',...
       'Position',[50 50 1400 380]);

subplot(1,4,1); imshow(f);
title('Original','FontSize',12);

for k = 1:3
    subplot(1,4,k+1);
    imshow(results_avg{k});
    title(sprintf('Avg %s', labels{k}),'FontSize',12);
end
sgtitle('twodSFilter - Low-Pass Averaging Filter','FontWeight','bold');

%% 5. Display - median filter
figure('Name','Median Filter - Project #6','NumberTitle','off',...
       'Position',[50 480 700 380]);

subplot(1,2,1); imshow(f);           title('Original','FontSize',12);
subplot(1,2,2); imshow(g_median);    title('Median 3x3','FontSize',12);
sgtitle('medianSFilter - Median Filter (3x3)','FontWeight','bold');

%% 6. Save images
if ~isfolder('images'), mkdir('images'); end
imwrite(f,              'images/original.png');
imwrite(results_avg{1}, 'images/avg_3x3.png');
imwrite(results_avg{2}, 'images/avg_11x11.png');
imwrite(results_avg{3}, 'images/avg_21x21.png');
imwrite(g_median,       'images/median_3x3.png');
fprintf('All images saved to images/\n');
